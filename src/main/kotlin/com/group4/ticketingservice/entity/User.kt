package com.group4.ticketingservice.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "users")

class User(name: String, id: String, password: String) : BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name
        protected  set

    @Column(nullable = false)
    var loginId: String = id
        protected  set

    @Column(nullable = false)
    var password: String = password
        protected  set

}