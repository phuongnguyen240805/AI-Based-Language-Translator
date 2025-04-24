package com.example.aibasedlanguagetranslator

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var ivNote: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Truy cập ImageView
        ivNote = findViewById(R.id.ivNote)

        // Thực hiện animation
        ivNote.alpha = 0f
        ivNote.animate().setDuration(1500).alpha(1f).withEndAction {
            // Chuyển đến MainActivity sau khi hoàn thành animation
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}