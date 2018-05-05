# PowerMacros
PowerMacros is plug-in for BurpSuite proxy. It provides similar but extended functionality of BurpSuite Macro feature. The main functionality is, that you are able to trigger one or more request before every request triggered from Intruder, Repeater, Scanner, Sequencer or Spider (except tools Proxy and Extender). You can extract data from arbitrary response of the request and replace or add data to the following request (replace CSRF token, session, add new header ...).

Integrates powerfully with Burp Suite's features, in particular its target scoping functionality. 

## Features
- create sequence of the request to be triggered before the every request call
- extract data from arbitrary response
- paste extracted data into arbitrary following request
  - replacement of the existing data
  - add new HTTP header
  - replacement of the HTTP header
- create new header
- changing order of the requests
- URL decoding of extracted data
- extracted data caching
	- you can specify time interval, when the extraction will be performed

## Advantages against the BurpSuite Macro
- ability to replace arbitrary string in the request
- ability to add new header into the request
- easier configuration than macro (does not seems to be, but it is ;))
- extracted data caching

## About the UI
The plug-in adds new tab into the BurpSuite named "PowerMacros". It contains several tabs: "Main", "Logger" and "Settings".

### Main
In the main window you are able to configure all the magic. The left part of the view is the "Extraction message list" and the right part is the "Replace message list".

#### Extraction message list
Here you can set up what requests will be triggered and what data will be extracted from their responses. After selecting the message, you can set the extraction by the selection of the response.

#### Replace message list
Here you can set up what data from extraction will be added/replaced in the following requests. The replace string can be set by the selection of the request.

### Logger
Logs all messaged what were modified or triggered by PowerMacros.

### Settings
He you can specifies what tool will use the PowerMacros plug-in.

## How to
1. Select messages e,g, in the Proxy tab, do right mouse click and select "Send to PowerMacros"
2. go to PowerMacros and click on the message in the "Extraction message list"
3. select data from the response editor
4. click "From selection" button
5. set extraction name and click "Add" button
6. click on the message in the "Replace message list"
7. select data from the request editor and click "From selection" button
8. set replace name and type (replace on the selected message)
9. select the extraction
10. click replaces "Add" button
11. now you are done and your request will be triggered, you can see it in the "Logger" tab.

## Screen-shots

Advanced URL Scoping Functionality. When the "in scope" URL conditions are met, user-specified string manipulation of outgoing requests will be triggered.

![Main tab](/screenshots/0_session_handling_rule_3_advanced.png?raw=true "Main tab")

Main: Example Extractions

![Logger tab](/screenshots/0_main_extract_example.png?raw=true "Logger tab")

Main: Example Replacements

![Settings tab](/screenshots/0_main_replace_example.png?raw=true "Settings tab")


## Coming Soon
-Python support