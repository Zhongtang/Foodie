jQuery("#simulation")
  .on("click", ".s-dfaebb38-c9ba-465e-ac32-994348c2d8c1 .click", function(event, data) {
    var jEvent, jFirer, cases;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-btnCancel")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/d12245cc-1680-458d-89dd-4f0d7fb22724",
                    "transition": "slideright"
                  }
                }
              ]
            }
          ]
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    } else if(jFirer.is("#s-btnSave")) {
      cases = [
        {
          "blocks": [
            {
              "actions": [
                {
                  "action": "jimNavigation",
                  "parameter": {
                    "target": "screens/dc7bc79a-d7e8-43e4-9e8e-fafd6c337db9",
                    "transition": "slideleft"
                  }
                }
              ]
            }
          ]
        }
      ];
      event.data = data;
      jEvent.launchCases(cases);
    }
  })
  .on("click", ".s-dfaebb38-c9ba-465e-ac32-994348c2d8c1 .toggle", function(event, data) {
    var jEvent, jFirer, cases;
    if(data === undefined) { data = event; }
    jEvent = jimEvent(event);
    jFirer = jEvent.getEventFirer();
    if(jFirer.is("#s-Image_15")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_15",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_16")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_16",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_17")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_17",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_18")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_18",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_19")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_19",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_20")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_20",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    } else if(jFirer.is("#s-Image_21")) {
      if(jFirer.data("jimHasToggle")) {
        jFirer.removeData("jimHasToggle");
        jEvent.undoCases(jFirer);
      } else {
        jFirer.data("jimHasToggle", true);
        event.backupState = true;
        event.target = jFirer;
        cases = [
          {
            "blocks": [
              {
                "actions": [
                  {
                    "action": "jimSetValue",
                    "parameter": {
                      "target": "#s-Image_21",
                      "value": "./images/b0754c1c-68c8-4b2d-87c1-c08b2cf8c850.png"
                    }
                  }
                ]
              }
            ]
          }
        ];
        jEvent.launchCases(cases);
      }
    }
  });