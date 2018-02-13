package com.example.hibernatedemo.view

import com.example.hibernatedemo.controllers.VendorService
import com.example.hibernatedemo.models.Vendor
import com.example.hibernatedemo.models.VendorModel
import javafx.collections.FXCollections
import tornadofx.*

class MainView : View("Hibernate Vendor List") {
    val vendorService: VendorService by inject()
    val vendors = FXCollections.observableArrayList<Vendor>()
    val selectedVendor = VendorModel()

    override val root = borderpane {
        top {
            hbox(spacing = 5) {
                button("_Add") {
                    action {
                        addVendor()
                    }
                }
                button("_Delete") {
                    enableWhen(selectedVendor.empty.not())
                    action {
                        deleteVendor()
                    }
                }
                button("_Refresh").action { onRefresh()}
            }
        }
        center {
            tableview(vendors) {
                column("Id", Vendor::idProperty)
                column("Name", Vendor::nameProperty).makeEditable()
                onEditCommit {
                    runAsync { vendorService.saveVendor(it) }
                }
                smartResize()
                bindSelected(selectedVendor)
            }
        }
    }

    private fun deleteVendor() {
        confirm("Confirm delete", "Do you want to delete ${selectedVendor.item.name}?") {
            runAsync {
                vendorService.deleteVendor(selectedVendor.item)
            } ui {
                onRefresh()
            }
        }
    }

    private fun addVendor() {
        val vendor = VendorModel(Vendor())

        dialog("Add vendor") {
            form {
                field("Name") {
                    textfield(vendor.name).required()
                }
                button("Add") {
                    isDefaultButton = true
                    action {
                        vendor.commit {
                            runAsync {
                                vendorService.saveVendor(vendor.item)
                            } ui {
                                onRefresh()
                                close()
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        onRefresh()
    }

    override fun onRefresh() {
        vendors.asyncItems { vendorService.listVendors() }
    }
}