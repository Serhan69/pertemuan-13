package com.industri.transportqa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etNik: EditText
    private lateinit var etName: EditText
    private lateinit var btnSubmitBooking: Button
    private lateinit var tvBookingStatus: TextView
    private val fareCalculator = FareCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNik = findViewById(R.id.etNik)
        etName = findViewById(R.id.etName)
        btnSubmitBooking = findViewById(R.id.btnSubmitBooking)
        tvBookingStatus = findViewById(R.id.tvBookingStatus)

        btnSubmitBooking.setOnClickListener {
            val nik = etNik.text.toString().trim()
            val name = etName.text.toString().trim()

            if (nik.isEmpty()) {
                tvBookingStatus.text = "NIK tidak boleh kosong"
                return@setOnClickListener
            }

            if (!fareCalculator.isValidNik(nik)) {
                tvBookingStatus.text = "NIK tidak valid"
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                tvBookingStatus.text = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            tvBookingStatus.text = "Pemesanan Tiket Berhasil Diverifikasi"
        }
    }
}
