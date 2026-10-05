package com.industri.transportqa

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var ticketDao: TicketDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        ticketDao = db.ticketDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetTicket() = runBlocking {
        val ticket = TicketEntity(
            ticketCode = "TKT-8801",
            passengerName = "Budi Santoso",
            destination = "Surabaya Gubeng"
        )
        ticketDao.insertTicket(ticket)

        val loaded = ticketDao.getTicketByCode("TKT-8801")
        assertThat(loaded).isNotNull()
        assertThat(loaded?.ticketCode).isEqualTo("TKT-8801")
        assertThat(loaded?.passengerName).isEqualTo("Budi Santoso")
        assertThat(loaded?.destination).isEqualTo("Surabaya Gubeng")
    }
}
