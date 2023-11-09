import {StyleSheet, Text, View} from "react-native";

const ChatRoomsScreen = () =>{

    return (

        <View style={styles.container}>
            <Text>ChatRoom List Page!!</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container : {
        alignItems : "center",
        justifyContent : "center"
    }
})

export default ChatRoomsScreen;