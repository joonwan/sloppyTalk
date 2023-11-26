import {Button, FlatList, SafeAreaView, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {useEffect, useState} from "react";
import axios from "axios";
import IP_ADDRESS from "./Const";
import AsyncStorage from "@react-native-async-storage/async-storage";

const getMemberId = async() =>{
    return await AsyncStorage.getItem("memberId");

}

const ChatRoomsScreen = ({navigation}) =>{

    const [chatRooms, setChatRooms] = useState([]);
    const [memberId, setMemberId] = useState("");
    useEffect( () => {

        const setting = async () =>{
            const memberId = await getMemberId();

            if(memberId != null){
                setMemberId(memberId);
                axios.get(
                    `http://${IP_ADDRESS}:8080/members/${memberId}/chatroom`,
                    {
                        headers:{
                            "content-type" : "Application/Json"
                        }
                    }
                )
                    .then(value => {
                        setChatRooms(value.data);

                    });
            }
        }

        setting();


    },[memberId]);

    const Item = ({item}) => (
        <TouchableOpacity style={styles.chat_room_container} onPress={()=>{
            console.log(typeof memberId);
            navigation.navigate(
                "ChatScreen",
                {
                    friendName: item.friendName,
                    friendId: item.friendId,
                    memberId : Number(memberId),
                    chatRoomId : item.chatRoomId,
            }
            )
        }}>
            <Text style={styles.name}>{item.friendName}</Text>
            <Text style={styles.text}>{item.content}</Text>
        </TouchableOpacity>
    )

    return (
        <SafeAreaView style={styles.container}>
            <FlatList data={chatRooms}
                      renderItem={({item}) => <Item item={item}/>}
                      keyExtractor={item => item.id}
            />
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    container : {
        alignItems : "center",
        justifyContent : "center",
        flex : 1
    },
    name: {
        fontSize:20,
        fontWeight:"bold"
    },
    text:{
      fontSize:15
    },
    chat_room_container:{
        borderWidth:2,
        width : 300,
        height : 70,
        marginVertical : 5,
        padding : 4,
        justifyContent:"center",
        alignItems :"flex-start",
        borderRadius : 10
    }
})

export default ChatRoomsScreen;