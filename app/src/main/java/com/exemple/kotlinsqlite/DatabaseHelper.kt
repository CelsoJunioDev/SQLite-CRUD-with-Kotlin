package com.exemple.kotlinsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME ="MyDb"
val TABLE_NAME ="Usuarios"
val COL_NOME ="nome"
val COL_IDADE ="idade"
val COL_ID ="id"

class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val criarTabela = "CREATE TABLE "+ TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOME +" VARCHAR(256)," +
                COL_IDADE +" INTEGER)"
        db?.execSQL(criarTabela)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun inserirDados(usuario: Usuario){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NOME, usuario.nome)
        cv.put(COL_IDADE, usuario.idade)
        var resultado = db.insert(TABLE_NAME, null,cv)
        if (resultado == -1.toLong())
            Toast.makeText(context, "Falha", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Sucesso", Toast.LENGTH_LONG).show()
    }

    fun lerDados() : MutableList<Usuario>{
        var list : MutableList<Usuario> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var usuario = Usuario()
                usuario.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                usuario.nome = result.getString(result.getColumnIndex(COL_NOME))
                usuario.idade = result.getString(result.getColumnIndex(COL_IDADE)).toInt()
                list.add(usuario)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun deletarDados(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun atualizarDados() {
       val db = this.writableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_IDADE, result.getInt(result.getColumnIndex(COL_IDADE))+1)
                db.update(TABLE_NAME, cv, COL_ID + "=? AND " + COL_NOME + "=?",
                arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                result.getString(result.getColumnIndex(COL_NOME))))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
}
