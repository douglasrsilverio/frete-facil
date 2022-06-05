package br.com.douglasti.fretefacil.mvp.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_constant")
data class FuelConstant(@PrimaryKey(autoGenerate = true) val id: Int,
                        val type: String) {

    constructor(type: String): this(id = 0, type = type)
}