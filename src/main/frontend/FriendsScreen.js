import {Button, FlatList, SafeAreaView, StyleSheet, Text, View} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import IP_ADDRESS from "./Const";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { ALERT_TYPE, Dialog, AlertNotificationRoot, Toast } from 'react-native-alert-notification';

async function getSessionId(){
    return await AsyncStorage.getItem("sessionId");
}



const FriendsScreen = ({route,navigation}) =>{
    const [chatRoomId, setChatRoomId] = useState(null);
    const [data, setData] = useState([]);
    const stompClient = useRef(null);
    const idParam = route.params;
    const memberId = idParam.memberId;
    async function createChatRoom(memberId, friendId, friendName) {
        console.log("memberId : " + memberId+ ", friendId : " + friendId);
        await axios.post(`http://${IP_ADDRESS}:8080/chatroom/new`,
            JSON.stringify({
                memberId,
                friendId
            }),{
                headers:{
                    "content-type" : "Application/Json"
                }
            }
        ).then(value => {

            const chatRoomId = value.data.chatRoomId;
            console.log(typeof memberId);
            navigation.navigate("ChatScreen", {
                friendName: friendName,
                friendId: friendId,
                memberId : memberId,
                chatRoomId : chatRoomId,
            })
        });
    }

    useEffect(() =>{
        async function setting(){
            return await getSessionId();
        }
        const promise = setting();

        promise.then(async (value) =>
            {
               try{
                   const result= await axios.get(`http://${IP_ADDRESS}:8080/members/${value}/friends`,
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

        const connect = () => {

            const sock = new SockJS(`http://${IP_ADDRESS}:8080/alert`);
            stompClient.current = Stomp.over(sock);

            stompClient.current.connect({
                memberId : memberId,
                screenName : "OTHER_SCREEN",
            }, frame => {
                console.log("Connected: " + frame);

                stompClient.current.subscribe(
                    `/private_alert/${memberId}`,
                    (message) => {
                        // const json = JSON.parse(message.body);
                        alert(JSON.stringify(message.body));
                        // Toast.show({
                        //     type: ALERT_TYPE.SUCCESS,
                        //     title: 'Success',
                        //     textBody: 'Congrats! this is toast notification success',
                        // })

                    }
                );
            }, error => {
                console.log("Error: " + error);
            });
        };

        connect();

        return () => {
            // Disconnect WebSocket when the component is unmounted
            if (stompClient.current && stompClient.current.connected) {
                stompClient.current.disconnect();
            }
        };

    },[]);
    useEffect(() => {},[chatRoomId]);
    const Item = ({friendId,friendName}) => (
        <View style={styles.member_container}>
            <Text style={styles.text}>{friendName}</Text>
            <Button title="chat" onPress={
                () => {
                    createChatRoom(memberId, friendId,friendName);
                }
            }
            />
        </View>
    )
    return (
        <SafeAreaView style={styles.container}>
            <FlatList data={data}
                      renderItem={({item}) => <Item friendId={item.friendId} friendName={item.friendName}/>}
                      keyExtractor={item => item.friendId}
            />
            <Button title="async" onPress={() => {
                async function setting(){
                    return await getSessionId();
                }
                const promise = setting();

                promise.then(async (value) =>
                    {
                        try{
                            const result= await axios.get(`http://${IP_ADDRESS}:8080/members/${value}/friends`,
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
            }}/>
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