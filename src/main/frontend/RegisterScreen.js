import {View, StyleSheet, TextInput, Button, Text} from "react-native";
import {useState} from "react";
import axios from "axios";

function register({name, loginId, password}){
    const data = JSON.stringify({name,loginId,password});

    axios.post(
        "http://localhost:8080/members/new",
        data,
        {
            headers:{
                'Content-Type': 'application/json'
            }
        })
        .then(console.log("success"))
        .catch(err => console.log("axios error : " + err));
}
const RegisterScreen = ({navigation}) => {

    const [name, onChangeName] = useState("");
    const [loginId, onChangeLoginId] = useState("");
    const [password, onChangePassword] = useState("");

    return (
        <View style={styles.container}>


                    <Text style={styles.text}>Name</Text>
                    <TextInput style={styles.input} onChangeText={onChangeName} value={name}/>


                    <Text style={styles.text}>Id</Text>
                    <TextInput style={styles.input} onChangeText={onChangeLoginId} value={loginId}/>


                    <Text style={styles.text}>Password</Text>
                    <TextInput style={styles.input} onChangeText={onChangePassword} value={password}/>


            <Button title="register" onPress={() => {
                register({name, loginId, password});
                navigation.goBack();
            }}/>
        </View>
    )
}

const styles = StyleSheet.create({
    container : {
        alignItems : "center",
        justifyContent : "center",
        flex:1
    },

    input : {
        borderWidth : 1,
        marginVertical : 13,
        borderRadius : 10,
        height:40,
        width:300
    },
    text:{
        fontSize:20,
        marginLeft : 10,
        fontWeight : "bold",
        textAlign:"center"
    }
})

export default RegisterScreen;