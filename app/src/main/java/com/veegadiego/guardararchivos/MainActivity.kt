package com.veegadiego.guardararchivos

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    internal lateinit var et_name:EditText
    internal lateinit var et_text:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_name = findViewById(R.id.et_name)
        et_text = this.findViewById(R.id.et_text)
    }

    private fun esExterno(): Boolean {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            Log.i("Stado", "Puede escribir")
            return true
        } else {
            return false
        }
    }

    fun GrabarEx(v: View) {
        if (esExterno() && Permisos(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val texto = File(Environment.getExternalStorageDirectory(), et_name.getText().toString())
            try {
                val arc = FileOutputStream(texto)
                arc.write(et_text.getText().toString().toByteArray())
                arc.close()

                Toast.makeText(this, "Archivo guardado", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else {
            Toast.makeText(this, "No pudo guardar en almacenamiento externo", Toast.LENGTH_SHORT).show()
        }

    }

    fun Permisos(permission: String): Boolean {
        val check = ContextCompat.checkSelfPermission(this, permission)
        return check == PackageManager.PERMISSION_GRANTED
    }
}
