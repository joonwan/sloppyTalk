import {Text, View, StyleSheet} from "react-native";

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