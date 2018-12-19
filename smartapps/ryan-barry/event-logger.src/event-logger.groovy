
definition(
    name: "Event Logger",
    namespace: "ryan-barry",
    author: "Ryan Barry",
    description: "Pull device capabilities. and other things, stuff n stuff",
    category: "Convenience",
)

preferences {
    section("Devices To Poll") {
		input "pollingDevices", "capability.temperatureMeasurement", multiple: true, title: "Pollable Devices"
	}
    section("Polling Interval (defaults to 15 min)") {
		input "pollingInterval", "decimal", title: "Number of minutes", required: false
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

    doPoll()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

    doPoll()
}

def doPoll() {

	
	log.debug "running doPoll"
    //log.debug(pollingDevices)
    
    for (device in pollingDevices)
    {
    	log.debug "Number of devices: ${pollingDevices}"
		def mySwitchCaps = device.capabilities
			
            log.debug "This Device Name: ${device.name}"
			// log each capability supported by the "mySwitch" device, along
			// with all its supported attributes
			mySwitchCaps.each {cap ->
    		log.debug "Capability name: ${cap.name}"
    		cap.attributes.each {attr ->
        	log.debug "-- Attribute name; ${attr.name}"
    		}
            
/*                    def attrs = device.supportedAttributes
        
        	attrs.each {
    			log.debug "${device.displayName}, attribute ${it.name}, values: ${it.values}"
    			log.debug "${device.displayName}, attribute ${it.name}, dataType: ${it.dataType}"
        	}
            */
            
            def state  = device.currentState("humidity")
            log.debug "humidity value as a string: ${state.value}"
            log.debug "humidity value date recorded ${state.date}"
            
            def tempState = device.currentState("temperature")
            log.debug "Temp value as a string: ${tempState.value}"
            log.debug "Temp value date recorded ${tempState.date}"
		}
        

    }

	//pollingDevices.poll()

    def timeOut = (pollingInterval != null && pollingInterval != "") ? pollingInterval : 15

	runIn( timeOut * 60, "doPoll",)
}
