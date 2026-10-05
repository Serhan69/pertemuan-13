package com.industri.transportqa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE ticketCode = :code")
    suspend fun getTicketByCode(code: String): TicketEntity?
}
