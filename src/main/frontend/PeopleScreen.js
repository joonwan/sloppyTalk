import {Text, View, StyleSheet, Button, FlatList, SafeAreaView} from "react-native";
import React, {useEffect, useState} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";

async function findPeople(sessionId){

    try{
        return await axios.get("http://localhost:8080/members/" + sessionId);

    }catch(e){
        console.log("axios error : " + e);
    }
}

async function getSessionId(){
    const sessionId = await AsyncStorage.getItem("sessionId");
    return sessionId;
}
const PeopleScreen = ({navigation}) => {

    const [data, setData] = useState([]);
    const [sessionId, setSessionId] = useState("");

    useEffect(() => {

        async function initSessionId(){
            return await getSessionId();
        }

        const result = initSessionId();
        result.then(value => {setSessionId(value)});


    },[sessionId, data]);

    const Item = ({memberName,memberId}) => (
        <View style={styles.member_container}>
            <Text style={styles.text}>{memberName}</Text>
            <Button title="follow" onPress={async () => {
                const targetId = memberId;
                const data = JSON.stringify({targetId})
                alert("follow!!");
                await axios.post(`http://localhost:8080/members/${sessionId}/follow`,
                    data,{
                        headers:{
                            'Content-Type': 'application/json'
                        }
                    });


                navigation.goBack();
            }}/>
        </View>
    );


    return (
        <SafeAreaView style={styles.container}>

            <View style={styles.searchSection}>
                <Button title="Search" onPress={() => {
                    const promise= findPeople(sessionId);
                    promise.then(value => setData(value.data));
                }}/>
            </View>
            <FlatList data={data}
                      renderItem={({item}) => <Item memberName={item.memberName} memberId={item.memberId}/>}
                      keyExtractor={item => item.memberid}
            />
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        alignItems:"center"
    },
    searchSection : {

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

export default PeopleScreen;