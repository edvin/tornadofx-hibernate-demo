package com.example.hibernatedemo.models

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Vendor(name: String? = null) {
    val idProperty = SimpleIntegerProperty()
    var id by idProperty

    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
}

class VendorModel(vendor: Vendor? = null) : ItemViewModel<Vendor>(vendor) {
    val id = bind(Vendor::idProperty)
    val name = bind(Vendor::nameProperty)
}
