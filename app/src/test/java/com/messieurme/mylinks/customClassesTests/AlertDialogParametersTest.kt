package com.messieurme.mylinks.customClassesTests

import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.ui.common.AlertDialogAddEditParameters
import org.junit.Before
import org.junit.Test

class AlertDialogParametersTest {
    val adParams = AlertDialogAddEditParameters()

    @Before
    fun `init alert dialog params`(){

    }

    @Test
    fun `editing link`(){
        assert(!(adParams.editingLink() || adParams.editingLF() || adParams.isEdit()))

        val link = Link("name", "link")
        adParams.startEditing(0, link)

        assert((adParams.isEdit() && adParams.editingLink()) && !adParams.editingLF())

        try {
            adParams.emptyOrVar("wrong command")
            assert(false)
        } catch (ignore: IllegalArgumentException){ }

        assert(adParams.emptyOrVar("linkName") == "name")
        assert(adParams.emptyOrVar("linkLink").contains(".+link".toRegex()))
        assert(adParams.emptyOrVar("lfName") == "")

        adParams.endEditing()

        assert(!(adParams.isEdit() || adParams.editingLink() || adParams.editingLF()))
    }

}