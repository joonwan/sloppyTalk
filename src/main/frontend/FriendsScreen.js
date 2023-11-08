import {Button, FlatList, SafeAreaView, StyleSheet, Text, View} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import React, {useEffect, useState} from "react";
import axios from "axios";

async function getSessionId(){
    return await AsyncStorage.getItem("sessionId");
}

const FriendsScreen = ({navigation}) =>{

    const [data, setData] = useState([]);
    const [sessionId, setSessionId] = useState("");

    useEffect(() =>{
        async function setting(){
            return await getSessionId();
        }
        const promise = setting();

        promise.then(async (value) =>
            {
               try{
                   const result= await axios.get(`http://localhost:8080/members/${value}/friends`,
                       {
                           headers:{
                               'Content-Type': 'application/json'
                           }
                       }
                   );
                   setData(result.data);
               }catch(e){
                   console.log("axios error : " + e);
               }

            }
        );

    },[]);

    const Item = ({friendId,friendName}) => (
        <View style={styles.member_container}>
            <Text style={styles.text}>{friendName}</Text>
            <Button title="chat" onPress={() => console.log("chat !!")}/>
        </View>
    )
    return (
        <SafeAreaView style={styles.container}>
            <FlatList data={data}
                      renderItem={({item}) => <Item friendId={item.friendId} friendName={item.friendName}/>}
                      keyExtractor={item => item.friendId}
            />
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    container:{
        alignItems:"center",
        justifyContent : "center",
        flex:1

    },
    member_container:{
        flex:1,
        borderWidth:2,
        width : 300,
        height : 80,
        marginVertical : 5,
        padding : 4,
        flexDirection : "row",
        justifyContent : "space-between",
        alignItems :"center",
        borderRadius : 10


    },
    text:{
        fontSize : 15
    }
})

export default FriendsScreen