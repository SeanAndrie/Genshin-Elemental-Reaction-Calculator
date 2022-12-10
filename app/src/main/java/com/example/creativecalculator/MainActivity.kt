package com.example.creativecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display splash screen
        displayPopupDialog()

        // Create empty mutable string and view lists
        var elemList = mutableListOf<String>()
        var elemView = mutableListOf<View>()

        // Placeholder variable for selected element
        var elemSelect = ""

        // For enabling or disabling selections
        var enableSelect = true

        // Initializing reaction and effect variables
        var reaction: String
        var effect: String

        // Restarts Selection
        fun restartSelection(){
            // Clear element list
            elemList.clear()
            enableSelect = true // Enable selection

            // Sets selected view images drawable to normal
            for (view in elemView){
                view.background = resources.getDrawable(R.drawable.elem_normal)
            }

            // Clear element image view list
            elemView.clear()
            textView.text = "" // Empty text
            textView2.text = ""
            elem1.text = ""
            elem2.text = ""
        }

        // Execute resulting reaction
        fun displayReaction(reaction:String, effect:String){
            textView.text = reaction
            textView2.text = effect
        }

        // Execute when elements do not result in a reaction
        fun noReactions(){
            textView.text = "No Reaction"
            textView2.text = "N/A"
        }

        // Conditions
        fun elementalReactions(elements: List<String>) {

            val baseElems = listOf<String>("anemo", "geo", "dendro")
            val otherElems = listOf<String>("pyro", "hydro", "cryo", "electro")

            println(otherElems)

            // At least one of the base elements
            if (elements.any {it in baseElems}){
                println(baseElems)
                // Swirl
                if (elements.contains("anemo") and elements.any{ it in otherElems}) {
                    reaction = "Swirl"
                    effect = "Deals extra elemental damage and spreads the effect"
                    displayReaction(reaction, effect)
                }

                // Crystallize
                else if(elements.contains("geo") and elements.any { it in otherElems}){
                        reaction = "Crystallize"
                        effect = "Creates a crystal that will provide a shield when picked up"
                        displayReaction(reaction, effect)
                }

                // Burning
                else if(elements.contains("dendro") and elements.contains("pyro")){
                    reaction = "Burning"
                    effect = "Deals AoE Pyro damage over time"
                    displayReaction(reaction, effect)
                }

                // Bloom
                else if(elements.contains("dendro") and elements.contains("hydro")){
                    reaction = "Bloom"
                    effect = "Creates a Dendro Core that explodes after 6 seconds, dealing AoE Dendro Damage"
                    displayReaction(reaction, effect)
                }

                // Quicken
                else if(elements.contains("dendro") and elements.contains("electro")){
                    reaction = "Quicken"
                    effect = "Applies a Quicken Aura"
                    displayReaction(reaction, effect)
                }

                // No Reactions i.e., (1) Pairing any of two base elements together
                else{
                    noReactions()
                }
            }

            // At least one of the other elements
           if (elements.any {it in otherElems}){
               println(otherElems)

               // Melt
               if (elements.contains("pyro") and elements.contains("cryo")){
                   reaction = "Melt"
                   effect = "Deals Extra Damage (2 times multiplier)"
                   displayReaction(reaction, effect)
               }

               // Vaporize
               else if(elements.contains("pyro") and elements.contains("hydro")){
                   reaction = "Vaporize"
                   effect = "Deals Extra Damage (1.5 times multiplier)"
                   displayReaction(reaction, effect)
               }

               // Overloaded
               else if(elements.contains("pyro") and elements.contains("electro")){
                   reaction = "Overloaded"
                   effect = "Deals AoE Pyro Damage"
                   displayReaction(reaction, effect)
               }

               // Electro-Charged
               else if(elements.contains("hydro") and elements.contains("electro")){
                   reaction = "Electro-Charged"
                   effect = "Deals Electro Damage over time"
                   displayReaction(reaction, effect)
               }

               // Freeze
               else if(elements.contains("hydro") and elements.contains("cryo")){
                   reaction = "Freeze"
                   effect = "Freezes the target."
                   displayReaction(reaction, effect)
               }

               // Superconduct
               else if(elements.contains("cryo") and elements.contains("electro")) {
                   reaction = "Superconduct"
                   effect  = "Deals AoE Cryo Damage and reduces the target's Physical Res by 50%"
                   displayReaction(reaction, effect)

               }

               // No reaction (Two similar elements)
               else if(elements[0] == elements[1]){
                   noReactions()
               }
           }
        }

        // Button for restarting the selection
        restartbtn.setOnClickListener{
            restartSelection()
        }

        // Toggle Image Select Drawable
        fun toggleSelect(element: ImageView, selected_elem:String){

            element.background = resources.getDrawable(R.drawable.elem_selected)

            // Append view and its corresponding string to their respective lists
            elemList.add(selected_elem)
            elemView.add(element)

            // disable selection when maximum list size (2) is reached
            if (elemList.size == 2){
                elementalReactions(elemList)
                enableSelect = false
                elem1.text = elemList[0].uppercase()
                elem2.text = elemList[1].uppercase()
            }
        }

        // Element OnClickListeners
        anemo.setOnClickListener {
            if (enableSelect) {
                elemSelect = "anemo"
                toggleSelect(anemo, elemSelect)
            }
        }

        cryo.setOnClickListener {
            if (enableSelect) {
                elemSelect = "cryo"
                toggleSelect(cryo, elemSelect)
            }
        }

        dendro.setOnClickListener {
            if (enableSelect) {
                elemSelect = "dendro"
                toggleSelect(dendro, elemSelect)
            }
        }

        electro.setOnClickListener {
            if (enableSelect) {
                elemSelect = "electro"
                toggleSelect(electro, elemSelect)
            }
        }

        geo.setOnClickListener {
            if (enableSelect) {
                elemSelect = "geo"
                toggleSelect(geo, elemSelect)
            }
        }

        hydro.setOnClickListener {
            if (enableSelect) {
                elemSelect = "hydro"
                toggleSelect(hydro, elemSelect)
            }
        }

        pyro.setOnClickListener {
            if (enableSelect) {
                elemSelect = "pyro"
                toggleSelect(pyro, elemSelect)
            }
        }
    }

    private fun displayPopupDialog(){

        // Get Animations from Anim Resource
        val popupAnim = AnimationUtils.loadAnimation(this, R.anim.pop_up_anim)
        val popdownAnim = AnimationUtils.loadAnimation(this, R.anim.pop_down_anim)

        // Create Dialog
        val dialog = Dialog(this)
        dialog.setCancelable(false)

        // Set content view of dialog to Pop-up layout
        dialog.setContentView(R.layout.popup)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))// Set transparent background

        val base = dialog.findViewById<ImageView>(R.id.base)
        val info = dialog.findViewById<TextView>(R.id.info)
        val paimon = dialog.findViewById<ImageView>(R.id.paimon)
        val quote = dialog.findViewById<TextView>(R.id.quote)
        val dismiss = dialog.findViewById<Button>(R.id.proceed)

        // Assign animations to all widgets in Pop-up layout
        base.startAnimation(popupAnim)
        info.startAnimation(popupAnim)
        paimon.startAnimation(popupAnim)
        quote.startAnimation(popupAnim)
        dismiss.startAnimation(popupAnim)

        // Dismiss button
        dismiss.setOnClickListener {

            // Closing animations
            base.startAnimation(popdownAnim)
            info.startAnimation(popdownAnim)
            paimon.startAnimation(popdownAnim)
            quote.startAnimation(popdownAnim)
            dismiss.startAnimation(popdownAnim)

            // Wait for 0.5s then dismiss pop up
            Handler(Looper.getMainLooper()).postDelayed({dialog.dismiss()}, 500)
        }

        dialog.show()
    }
}