import LoginScreen from "./LoginScreen";
import {NavigationContainer} from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import RegisterScreen from "./RegisterScreen";
import FriendsScreen from "./FriendsScreen";
import {createBottomTabNavigator} from "@react-navigation/bottom-tabs";
import ChattingScreen from "./ChattingScreen";
import {Button} from "react-native";
import PeopleScreen from "./PeopleScreen";

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();
const  App = () =>{

  return (
   <NavigationContainer>
     <Stack.Navigator>
       <Stack.Screen name="LoginScreen" component={LoginScreen}/>
       <Stack.Screen name="RegisterScreen" component={RegisterScreen}/>
       <Stack.Screen name="FriendsScreen" component={FriendsScreen}/>
       <Stack.Screen name="PeopleScreen" component={PeopleScreen} />
       <Stack.Screen name="MyTabs" component={MyTabs} options={{headerShown:false}}/>
     </Stack.Navigator>
   </NavigationContainer>
  );
}

const MyTabs = ({navigation}) =>{
    return (
        <Tab.Navigator>
            <Tab.Screen name="친구" component={FriendsScreen} options={{
                headerRight:() => (
                    <Button title="People" onPress={() => navigation.navigate("PeopleScreen")} />
                )
            }}/>
            <Tab.Screen name="채팅" component={ChattingScreen}/>
        </Tab.Navigator>
    )
}

export default App;
