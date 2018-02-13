package com.example.hibernatedemo.controllers

import com.example.hibernatedemo.models.Vendor
import org.hibernate.Session
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import tornadofx.*

class VendorService : Controller() {
    private val registry = StandardServiceRegistryBuilder().configure().build()
    private val sessionFactory = MetadataSources(registry).buildMetadata().buildSessionFactory()

    init {
        insertTestData()
    }

    fun shutdown() {
        sessionFactory.close()
    }

    private fun insertTestData() {
        saveVendor(Vendor("Oracle"))
        saveVendor(Vendor("Microsoft"))
    }

    fun listVendors() = withTransaction {
        createQuery("from Vendor", Vendor::class.java).list().observable()
    }

    fun saveVendor(vendor: Vendor) = withTransaction {
        save(vendor)
    }

    fun deleteVendor(vendor: Vendor) = withTransaction {
        delete(vendor)
    }

    private fun <T> withTransaction(fn: Session.() -> T): T {
        val session = sessionFactory.openSession()
        session.beginTransaction()
        try {
            return fn(session)
        } finally {
            session.transaction.commit()
            session.close()
        }
    }
}