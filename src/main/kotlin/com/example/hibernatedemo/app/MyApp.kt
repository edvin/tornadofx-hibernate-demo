package com.example.hibernatedemo.app

import com.example.hibernatedemo.controllers.VendorService
import com.example.hibernatedemo.view.MainView
import tornadofx.*

class MyApp: App(MainView::class) {
    val vendorService: VendorService by inject()

    override fun stop() {
        vendorService.shutdown()
        super.stop()
    }
}