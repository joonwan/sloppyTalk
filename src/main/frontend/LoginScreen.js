import React, {useState} from "react";
import {StyleSheet, View, Text, TextInput, Button} from "react-native";

function login() {
    console.log("login!!")
}

const LoginScreen = ({navigation}) => {

    const [id, onChangeId] = useState("");
    const [password, onChangePassWord] = useState("");

    return(
        <View style={styles.container}>
            <TextInput style={styles.input}
                       onChangeText={onChangeId}
                       value={id}
            placeholder=" id"/>

            <TextInput style={styles.input}
                       onChangeText={onChangePassWord}
                       value={password}
                       placeholder=" password"/>
            <Button title="Submit" onPress={login}/>
        </View>

    )
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems:"center",
        justifyContent:"center"
    },
    input : {
        borderWidth:1,
        marginVertical:10,
        height:40,
        width:300,
        borderRadius:10

    }

});

export default LoginScreen;