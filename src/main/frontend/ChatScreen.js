import {Button, FlatList, StyleSheet, Text, TextInput, View} from "react-native";
import {useEffect, useState} from "react";
import SockJs from "sockjs-client"
import Stomp from "stompjs"
import IP_ADDRESS from "./Const";

var stompClient = null;


const getChatRoomId = (memberData) => {

    const memberId = memberData.memberId;
    if(memberId === 3){
        return 2;
    }
    return 1;
}


const ChatScreen = ({route},navigation) =>{
    const {friendName,friendId,memberData} = route.params;

    let data = [
        {id : 1, fromId : 1, content : "hello!!"},
        {id : 2, fromId : 2, content : "hello my friend!!"},
        {id : 3, fromId : 2, content : "What are you doing?"},
        {id : 4, fromId : 1, content : "Shut up"},
    ];

    const [text, onChangeText] = useState("");
    const [initData, setInitData] = useState(data);

    const connect = (memberData) => {
        const sock = new SockJs(`http://${IP_ADDRESS}:8080/sloppy-gate`);
        stompClient = Stomp.over(sock);
        stompClient.connect({},
            frame => {
                const chatRoomId= getChatRoomId(memberData);
                console.log(chatRoomId)

                stompClient.subscribe(
                    "/topic/global",
                    (message) => {

                        console.log("message : " + JSON.stringify(message.body));
                        const messageData = JSON.parse(message.body);
                        // const id = initData.length+1;
                        // console.log("flat list : " + id);
                        // addElement({id,messageData});


                    }
                )
            })
    }



    const addElement = ({id,messageData}) =>{
        const fromId = messageData.fromId;
        const content = messageData.content;
        console.log(fromId);
        console.log(content);

        const newArray = [...initData, {id:id,fromId:fromId,content:content}];
        console.log(newArray);
        setInitData(newArray);

    }

    // const addElement = ({id,text}) =>{
    //     let newArray = [...initData, {id:id,fromId:2,content:text}];
    //     setInitData(newArray);
    // }
    useEffect(() => {
        console.log(JSON.stringify(initData));
    },[initData])
    useEffect(() => {
        connect(memberData);
    },[])
    const Content = ({fromId,content}) => (
        <View style={styles.message_container}>

            {fromId === memberData.memberId ?
                <View style={styles.my_message_box}>
                    <Text style={styles.name} >me</Text>
                    <View style={styles.content_box}>
                        <Text style={styles.content}>{content}</Text>
                    </View>
                </View>
                :
                <View style={styles.other_message_box}>
                    <Text style={styles.name}>{friendName}</Text>
                    <View style={styles.content_box}>
                        <Text style={styles.content}>{content}</Text>
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
                    renderItem={({item}) => <Content fromId={item.fromId} content={item.content}/>}
                    keyExtractor={item => item.id}
                    extraData={initData}
                />
            </View>


            <View style={styles.input_container}>
                <View  style={styles.input_box}>
                    <TextInput value={text} onChangeText={onChangeText} style={styles.t_input} />
                </View>
               <View style={styles.button}>
                   <Button title="send" onPress={() => {

                        stompClient.send("/ws/global",{}, JSON.stringify({
                            fromId : memberData.memberId,
                            toId : friendId,
                            content : text
                        })
                        )
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