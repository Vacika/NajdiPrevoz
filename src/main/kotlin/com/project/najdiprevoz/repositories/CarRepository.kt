package com.project.najdiprevoz.repositories

import com.project.najdiprevoz.domain.Car
import com.project.najdiprevoz.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CarRepository : JpaRepository<Car, Long> {

    fun findByOwner_Id(ownerId: Long): Optional<Car>
    fun findByOwner(owner: Member): Car?
    fun deleteCarByOwner_Id(ownerId: Long)
}