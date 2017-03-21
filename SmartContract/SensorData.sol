pragma solidity ^0.4.4;

contract SensorData {

    //sample entity made to store sensor data
    struct SensorData {
        uint sensorId;
        string sensorValue;
        string sensorName;
        string sensorType;
        uint gatewayID;
    }

    //threshold value set by government
    uint public threshold=30;

    //array of sensor data , this is strictly for demo purpose for hackathon , will be changed in actual implementation
    SensorData[] public arrSensorData;

    function addSensorData(uint _sensorId, string _sensorValue, string _sensorName, string _sensorType,uint  _gatewayID) public returns(uint) {
        arrSensorData.length++;
        arrSensorData[arrSensorData.length-1].sensorId = _sensorId;
        arrSensorData[arrSensorData.length-1].sensorValue = _sensorValue;
        arrSensorData[arrSensorData.length-1].sensorName = _sensorName;
        arrSensorData[arrSensorData.length-1].sensorType = _sensorType;
        arrSensorData[arrSensorData.length-1].gatewayID = _gatewayID;
        return arrSensorData.length;
    }


//get all data stream count
    function getDataStreamCount() public constant returns(uint) {
        return arrSensorData.length;
    }


//get current sensor threshold value set
    function getSensorThresholdValue() public constant returns(uint) {
        return threshold;
    }


//set sensor threshold value set
    function setSensorThresholdValue(uint newthreshold )  {
       threshold=newthreshold;
    }


    
}