package com.rac.nugg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Composable
fun Ascreen(navHostController: NavHostController,sharedViewModel: SharedViewModel){

 var categorylist by remember {
     mutableStateOf(emptyList<Categorydata>())

 }

    listitem { categories ->
        categorylist = categories
    }

    LazyColumn(content = {
        items(categorylist) { item ->
            home(name = item.name,item.videoLink,navHostController = navHostController,sharedViewModel=sharedViewModel)
        }
    })




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home(
    name: String,
    videoLink: String,
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel
){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
                .padding(15.dp),

            onClick = {

                sharedViewModel.setVideoLink(videoLink)

                navHostController.navigate("B")



            }

        ) {


            Itemdescrption(name, modifier = Modifier)

        }
    }
}

@Composable
 fun Itemdescrption(name: String,modifier: Modifier) {
    Column {

        Text(
            text = name, fontSize = 30.sp, fontWeight = FontWeight.Bold,
            color = Color.Black
        )

    }
}

data class Categorydata(val name: String,val videoLink:String)

fun listitem(callback:(List<Categorydata>) -> Unit){
    val database = FirebaseDatabase.getInstance().reference.child("user")

    database.addValueEventListener(object :ValueEventListener{

        override fun onDataChange(snapshot: DataSnapshot) {

            val list = mutableListOf<Categorydata>()

            for (dataSnapshot in snapshot.children){
                val name = dataSnapshot.child("name").getValue(String::class.java)
                val videoLink = dataSnapshot.child("videoLink").getValue(String::class.java)

                name?.let {
                    list.add(Categorydata(it,videoLink?:""))
                }
            }

            callback(list)


        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
}