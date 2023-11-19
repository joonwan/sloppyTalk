import React, { useState, useEffect, useRef } from 'react';
import { View, Text, FlatList, TextInput, Button, StyleSheet } from 'react-native';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import IP_ADDRESS from "./Const";
import axios from "axios";

const ChatScreen = ({ route }) => {
    const { friendName, friendId, memberId, chatRoomId } = route.params;

    const [text, onChangeText] = useState("");
    const [messages, setMessages] = useState([]);
    const stompClient = useRef(null);
    const nextId = useRef(0);

    useEffect(() => {
        const connect = () => {

            axios.get(
                `http://${IP_ADDRESS}:8080/chatroom/${chatRoomId}/messages`,
                {
                    headers:{
                        "content-type" : "Application/Json"
                    }
                }
            ).then(
                response => {
                    console.log(response.data);
                    setMessages(response.data.messageDataList);
                    nextId.current = response.data.startId;


                }
            ).catch(
                e => console.log("axios error : " + e)
            )

            const sock = new SockJS(`http://${IP_ADDRESS}:8080/sloppy-gate`);
            stompClient.current = Stomp.over(sock);

            stompClient.current.connect({}, frame => {
                console.log("Connected: " + frame);

                stompClient.current.subscribe(
                    `/chat_room/${chatRoomId}`,
                    (message) => {
                        const json = JSON.parse(message.body);
                        setMessages(prevMessages => [...prevMessages, { id: nextId.current, fromId: json.fromId, content: json.content }]);
                        nextId.current += 1;
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
    }, [chatRoomId]);

    const sendMessage = () => {
        if (text.trim() !== "" && stompClient.current && stompClient.current.connected) {
            stompClient.current.send("/ws/private", {}, JSON.stringify({
                fromId: memberId,
                toId: friendId,
                content: text,
                chatRoomId: chatRoomId
            }));
            onChangeText("");
        }
    };

    const MessageComponent = ({ fromId, content }) => (
        <View style={fromId === memberId ? styles.myMessageBox : styles.otherMessageBox}>
            <Text style={styles.name}>{fromId === memberId ? 'Me' : friendName}</Text>
            <View style={styles.contentBox}>
                <Text style={styles.content}>{content}</Text>
            </View>
        </View>
    );

    return (
        <View style={styles.container}>
            <View style={styles.chatContainer}>
                <FlatList
                    data={messages}
                    renderItem={({ item }) => <MessageComponent fromId={item.fromId} content={item.content} />}
                    keyExtractor={(item) => item.id}
                />
            </View>

            <View style={styles.inputContainer}>
                <View style={styles.inputBox}>
                    <TextInput value={text} onChangeText={onChangeText} style={styles.textInput} />
                </View>
                <View style={styles.button}>
                    <Button title="Send" onPress={sendMessage} />
                </View>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center"
    },
    chatContainer: {
        flex: 7,
    },
    inputContainer: {
        flex: 1,
        borderWidth: 1,
        flexDirection: "row",
        alignItems: "center",
    },
    inputBox: {
        flex: 4,
        justifyContent: "center",
    },
    button: {
        flex: 1,
        marginHorizontal: 4,
    },
    textInput: {
        borderWidth: 1,
        borderRadius: 10,
        height: 35,
        marginHorizontal: 4,
    },
    myMessageBox: {
        margin: 4,
        justifyContent: "center",
        alignItems: "flex-end",
        padding: 10
    },
    otherMessageBox: {
        margin: 4,
        borderRadius: 10,
        justifyContent: "center",
        alignItems: "flex-start",
        padding: 10
    },
    contentBox: {
        borderWidth: 1,
        margin: 10,
        borderRadius: 15,
        minHeight: 20,
        padding: 10,
        maxWidth: 200
    },
    name: {
        fontSize: 12
    },
    content: {
        fontSize: 20
    },
});

export default ChatScreen;
