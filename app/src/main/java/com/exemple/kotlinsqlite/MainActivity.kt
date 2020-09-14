package com.exemple.kotlinsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        var db = DatabaseHelper(context)
        buttonInserir.setOnClickListener {
            if (editNome.text.toString().length > 0 &&
                    editIdade.text.toString().length > 0){
                var usuario = Usuario(editNome.text.toString(), editIdade.text.toString().toInt())

                db.inserirDados(usuario)
            }else{
                Toast.makeText(context, "Insira algum dado", Toast.LENGTH_LONG).show()
            }
        }

      buttonLer.setOnClickListener {
          var dado = db.lerDados()
          textResultado.text=""
          for (i in 0..dado.size-1){
              textResultado.append(dado.get(i).id.toString() + " " +dado.get(i).nome + " " +dado.get(i).idade +"\n")
          }

      }
        buttonAtualizar.setOnClickListener {
            db.atualizarDados()
            buttonLer.performClick()
        }

        buttonDeletar.setOnClickListener {
            db.deletarDados()
            buttonLer.performClick()
        }
    }
}