import {Text, View, StyleSheet, Button} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";

async function getSessionId(){
    const sessionId= await AsyncStorage.getItem("sessionId");
    alert(sessionId);
}

const FriendsScreen = ({navigation}) =>{
    return (
        <View style={styles.container}>
            <Text>
                Friends Screen!!
            </Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        alignItems:"center",
        justifyContent : "center"
    }
})

export default FriendsScreen