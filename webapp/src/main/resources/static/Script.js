let exercises = null;

    function setLoading() {
        const elem = document.createElement('DIV');
        elem.id = 'loading';
        document.body.appendChild(elem);
    }

    function setNotLoading() {
        const elem = document.getElementById('loading');
        elem.parentElement.removeChild(elem);
    }

    function loadExercises() {
        const xhr = new XMLHttpRequest();
        const exercisesPath = "/exercise/getExercises";
        let url;
        // If we're in local development, point at a local webserver.
        if (document.location.href.startsWith("file:///")) {
            url = "http://localhost:8080" + exercisesPath;
        } else {
            url = exercisesPath
        }
        xhr.addEventListener("load", onExercisesLoaded);
        xhr.open("GET", url, true);
        setLoading();
        xhr.send(null);
    };

    function onExercisesLoaded() {
        setNotLoading();
        if ((this.readyState !== 4 || this.status !== 200)) {
            // TODO: Actual error handling
            console.error(xhr.status);
            return;
        }
        exercises = JSON.parse(this.responseText);
        // Populate Select Field
        const exerciseList = document.getElementById("ExerciseList");

        for (const [index, details] of exercises.entries()) {
            const elem = document.createElement("option");
            elem.value = index;
            elem.text = details.title;
            exerciseList.appendChild(elem);
        }
        setExercise(0);

        
    }

    function setExercise(exerciseNumber) {
        const selectedExerciseProperties = exercises[exerciseNumber];
        document.getElementById("Description").textContent = selectedExerciseProperties.description;

        const precedingCodeBox = document.getElementById("preceding-code-box");
        if (selectedExerciseProperties.precedingCode) {
            precedingCodeBox.innerHTML = selectedExerciseProperties.precedingCode;
            precedingCodeBox.style.visibility = "visible";
        } else {
            precedingCodeBox.style.visibility = "hidden";
        }

        const followingCodeBox = document.getElementById("following-code-box");
        if (selectedExerciseProperties.followingCode) {
            followingCodeBox.innerHTML = selectedExerciseProperties.followingCode;
            followingCodeBox.style.visibility = "visible";
        } else {
            followingCodeBox.style.visibility = "hidden";
        }

        const textbox = document.getElementById("input");
        const dropdowns = document.getElementById("inputEPE");
        if(exerciseNumber==6){
            textbox.style.display = "none";
            dropdowns.style.display = "block";
            loadDropDowns();
        }else{
            textbox.style.display = "block";
            dropdowns.style.display = "none";
        }
    }

    function onSelectedExerciseChange() {
        const list = document.getElementById("ExerciseList");
        const exerciseNumber = parseInt(list.options[list.selectedIndex].value, 10);
        setExercise(exerciseNumber);
    }

    function findDropdownOptions() {
        const xhr = new XMLHttpRequest();
        const getDropdownListMembers = "/exercise/getDropdownListMembers";
        let url;
        // If we're in local development, point at a local webserver.
        if (document.location.href.startsWith("file:///")) {
            url = "http://localhost:8080" + getDropdownListMembers;
        } else {
            url = getDropdownListMembers;
        }
        xhr.open("GET", url, true);
        setLoading();
        xhr.send(null);
    };

    function loadDropDowns() {
        clearDropDowns();
        const div = document.getElementById("inputEPE");
        const eod = document.getElementById("endofdrops");
        var i=0;
        var j=0;
        var n=5;
        dropdownList = JSON.parse(findDropdownOptions.responseText);

        for(j=0 ; j < n ; j++){
            var select = document.createElement("select");
            select.className = "epe";
            select.id = "epe"+(j+1).toString();
            for(i=0 ; i < dropdownList.length ; i++){
                const elem = document.createElement("option");
                elem.id = i;
                elem.value = dropdownList[i];
                elem.text = dropdownList[i];
                select.appendChild(elem);
            }
            div.insertBefore(select, eod);
        }
    }

    function clearDropDowns() {
        const div = document.getElementById("inputEPE");
        var drops = div.getElementsByClassName("epe");
        var k = 0;
        var n = drops.length;
        for(k=0 ; k < n ; k++){
            div.removeChild(drops[0]);
        }
    }

    function postAnswer() {
        resetOutput();

        if (document.getElementById("input-box").value === "") {
            window.location.href = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            return;
        }
        const list = document.getElementById("ExerciseList");
        const exerciseNumber = parseInt(list.options[list.selectedIndex].value, 10);
        const path = exercises[exerciseNumber].url;
        // If we're in local development, point at a local webserver.
        const url = document.location.href.startsWith("file:///") ? "http://localhost:8080/" + path : path;
        console.log(url);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener("load", onResultLoaded);
        var exercise = JSON.stringify({ input: document.getElementById("input-box").value });
        xhr.open("POST", url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(exercise);
        setLoading();
    }

    function postAnswerEPE() {
        resetOutput();
        if (document.getElementById("epe1").value === "" &&
        document.getElementById("epe2").value === "" &&
        document.getElementById("epe3").value === "" &&
        document.getElementById("epe4").value === "" &&
        document.getElementById("epe5").value === "") {
            window.location.href = "https://www.youtube.com/watch?v=C9xHlCAcwjw";
            return;
        }

        const list = document.getElementById("ExerciseList");
        const exerciseNumber = parseInt(list.options[list.selectedIndex].value, 10);
        const path = exercises[exerciseNumber].url;
        // If we're in local development, point at a local webserver.
        const url = document.location.href.startsWith("file:///") ? "http://localhost:8080/" + path : path;
        console.log(url);
        const xhr = new XMLHttpRequest();
        xhr.addEventListener("load", onResultLoaded);
        var exercise = JSON.stringify({input:
        document.getElementById("epe1").value +
        document.getElementById("epe2").value +
        document.getElementById("epe3").value +
        document.getElementById("epe4").value +
        document.getElementById("epe5").value});
        xhr.open("POST", url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(exercise);
        setLoading();
    }

    function onResultLoaded() {
        setNotLoading();
        if (this.readyState !== 4 || this.status !== 200) {
            // TODO: Actual error handling
            console.error(xhr.status);
            return;
        }
        const response = JSON.parse(this.responseText);
        buildOutput(response);
    }

    function buildOutput(response) {
        if (response.output) {
            document.getElementById('runner-output').innerHTML = response.output;
            makeVisible('runner-output');
        }
        if (response.succesfulSolution) {
            document.getElementById("correct-answer").innerHTML = "Correct answer. Well done!";
            makeVisible("correct-answer");
        } else {
            const incorrectAnswer = document.getElementById("incorrect-answer");
            incorrectAnswer.innerHTML = ("Incorrect answer");
            if (response.compilerErrors && response.compilerErrors.length) {
                response.compilerErrors.forEach(error => {
                incorrectAnswer.innerHTML += ("<br>Error on line: " + error.lineNumber + "<br>The compiler error description was: " + error.message + "<br>");
            });
        }
        if (response.runnerErrors && response.runnerErrors.length) {
            response.runnerErrors.forEach(error => {
            incorrectAnswer.innerHTML += ("<br>The runner error description was: " + error.message + "<br>");
            });
        }
        makeVisible("incorrect-answer");
        }

        if (response.compilerInfo && response.compilerInfo.length) {
            response.compilerInfo.forEach(info => {
                document.getElementById("information").innerHTML += ("Information about line: " + info.lineNumber + "<br>this come from here: " + info.message + "<br>");
        });
        makeVisible("information");
        }
    }

    function makeVisible(elementID) {
        document.getElementById(elementID).style = "display:block";
    }

    function resetOutput() {
        const elements = ["runner-output", "correct-answer", "incorrect-answer", "information"]
        elements.forEach(element => {
        document.getElementById(element).style = "display:none";
        document.getElementById(element).innerHTML = "";
        });
    }

    function onLoad() {
        document.getElementById("ExerciseList").onchange = onSelectedExerciseChange;
        document.getElementById("enter-answer").onclick = postAnswer;
        document.getElementById("enter-answer-epe").onclick = postAnswerEPE;
        setNotLoading();
        loadExercises();
    }
    
    window.onload = onLoad;
