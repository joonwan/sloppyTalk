import {Button, FlatList, StyleSheet, Text, TextInput, View} from "react-native";
import {useEffect, useState} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

const getSessionId = async () =>{
    return await AsyncStorage.getItem("sessionId");
}

const ChatScreen = ({route},navigation) =>{
    const {friendName,friendId} = route.params;
    const [sessionId, setSessionId] = useState("");
    const data = [
        {id : 1, value : "a", sessionId : "asd"},
        {id : 2, value : "b",sessionId : "asd"},
        {id : 3, value : "c",sessionId : "asd"},
        {id : 4, value : "dasfdoiqnwfoqejfenqofvjqenfqeojqencojqnfqjqeojfqnefq",sessionId : "asd"},
    ];

    const [text, onChangeText] = useState("");
    const [id, setId] = useState(5);
    const [initData, setInitData] = useState(data);

    const addElement = ({id,text}) =>{
        let newArray = [...initData, {id:id,value:text,sessionId:sessionId}];
        setInitData(newArray);
    }

    useEffect(() =>{
        const promise = getSessionId();
        promise.then(value => setSessionId(value));
    },[initData])
    const Content = ({text,param_sessionId}) => (
        <View style={styles.message_container}>

            {param_sessionId === sessionId ?
                <View style={styles.my_message_box}>
                    <Text style={styles.name} >me</Text>
                    <View style={styles.content_box}>
                        <Text style={styles.content}>{text}</Text>
                    </View>
                </View>
                :
                <View style={styles.other_message_box}>
                    <Text style={styles.name}>{friendName}</Text>
                    <View style={styles.content_box}>
                        <Text style={styles.content}>{text}</Text>
                    </View>

                </View>
            }
        </View>

    )

    return (
        <View style={styles.container}>
            <View style={styles.chat_container}>
                <FlatList
                    data={initData}
                    renderItem={({item}) => <Content text={item.value} param_sessionId={item.sessionId}/>}
                    keyExtractor={item => item.id}
                />
            </View>


            <View style={styles.input_container}>
                <View  style={styles.input_box}>
                    <TextInput value={text} onChangeText={onChangeText} style={styles.t_input} />
                </View>
               <View style={styles.button}>
                   <Button title="send" onPress={() => {
                       addElement({id, text});
                       setId(id+1);
                       onChangeText("");
                   }}/>
               </View>

            </View>
    </View>
)


}

const styles = StyleSheet.create({
    container : {
        flex : 1,
        justifyContent : "center"
    },
    chat_container : {
        flex:7,

    },
    input_container:{
        flex:1,
        borderWidth:1,
        flexDirection : "row",
        alignItems:"center",


    },
    input_box : {
        flex : 4,
        justifyContent:"center",


    },
    button : {
        flex : 1,
        marginHorizontal:4,

    },
    t_input : {
        borderWidth : 1,
        borderRadius : 10,
        height:35,
        marginHorizontal:4,

    },
    my_message_box:{
        margin : 4,
        justifyContent:"center",
        alignItems:"flex-end",
        padding:10
    },
    other_message_box:{
        margin : 4,
        borderRadius :10,
        justifyContent:"center",
        alignItems:"flex-start",
        padding:10
    },
    message_container:{
        margin:3
    },
    name : {
        fontSize:12
    },
    content:{
        fontSize:20
    },
    content_box : {
        borderWidth:1,
        margin :10,
        borderRadius:15,
        minHeight:20,
        padding:10,
        maxWidth:200
    }
})

export default ChatScreen;