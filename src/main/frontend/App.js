import LoginScreen from "./LoginScreen";
import {NavigationContainer} from "@react-navigation/native";
import {createNativeStackNavigator} from "@react-navigation/native-stack";

const  App = () =>{
  const Stack = createNativeStackNavigator();
  return (
   <NavigationContainer>
     <Stack.Navigator>
       <Stack.Screen name="LoginScreen" component={LoginScreen}/>
     </Stack.Navigator>
   </NavigationContainer>
  );
}

export default App;
