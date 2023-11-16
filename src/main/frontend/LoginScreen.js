import React, {useEffect, useState} from "react";
import {StyleSheet, View, Text, TextInput, Button} from "react-native";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import IP_ADDRESS from "./Const";


const storeSessionId = async (sessionId) => {
    try {
        console.log("store : " + sessionId);
        await AsyncStorage.setItem("sessionId", sessionId);
    } catch (e) {
        console.log("async storage store data error : " + e);
    }
}

const getSessionId = async () => {
    try {
        const sessionId = await AsyncStorage.getItem("sessionId");
        console.log("find : " + sessionId);
        return sessionId;
    } catch (e) {
        console.log("async storage get data error : " + e);
    }
}

const clearStore = async () => {
    try {
        console.log("clearStore!!")
        await AsyncStorage.removeItem("sessionId");
    } catch (e) {
        console.log("async storage clear data error : " + e);
    }
}

const LoginScreen = ({navigation}) => {

    const [loginId, onChangeLoginId] = useState("");
    const [password, onChangePassWord] = useState("");
    const [sessionId, setSessionId] = useState(null);
    useEffect(() => {
        clearStore();
    }, [])

    async function login({loginId, password, sessionId}) {

        const data = JSON.stringify({loginId, password, sessionId})


        const json = await axios.post(
            `http://${IP_ADDRESS}:8080/login`,
            data,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        );

        const receiveMemberId = await json.data.memberId;
        const receiveSessionId = await json.data.sessionId;
        const receiveIsLogin = await json.data.login;

        return [receiveIsLogin, receiveSessionId, receiveMemberId];


    }

    return (
        <View style={styles.container}>
            <TextInput style={styles.input}
                       onChangeText={onChangeLoginId}
                       value={loginId}
                       autoCapitalize="none"
                       placeholder=" id"/>

            <TextInput style={styles.input}
                       onChangeText={onChangePassWord}
                       value={password}
                       autoCapitalize="none"
                       placeholder=" password"/>


            <View style={styles.buttonContainer}>
                <View style={styles.buttonBox}>
                    <Button title="login" onPress={async () => {
                        console.log("====login=====")
                        try {
                            const [rIsLogin, rSessionId, rMemberId] = await login({loginId, password, sessionId});

                            await storeSessionId(rSessionId);
                            if (rIsLogin) {
                                setSessionId(rSessionId);
                                navigation.navigate("MyTabs",{
                                    screen:"친구",
                                    params:{
                                        memberId : rMemberId
                                    }
                                });
                            }

                        } catch (e) {
                            console.log("login error = " + e);
                            navigation.navigate("LoginScreen");
                        }

                    }}/>
                </View>

                <View style={styles.buttonBox}>
                    <Button title="register" onPress={() => navigation.navigate("RegisterScreen")}/>
                </View>

            </View>

        </View>

    )
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center"
    },
    input: {
        borderWidth: 1,
        marginVertical: 10,
        height: 40,
        width: 300,
        borderRadius: 10
    },

    buttonContainer: {
        flexDirection: "row",
        marginVertical: 10
    },
    buttonBox: {
        marginHorizontal: 10
    },

    button: {
        marginHorizontal: 10
    }

});

export default LoginScreen;