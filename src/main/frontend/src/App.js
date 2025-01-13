import './App.css';
import React, { useState } from "react";
import Editor from '@monaco-editor/react';
import Display from './Display';
const App = () => {
  // defaults
  const [enteredInput, setEnteredInput] = useState("");
  const [username, setUsername] = useState("");
  const [enteredCode, setEnteredCode] = useState("");
  const [output, setOutput] = useState(null);
  const [user, setUser] = useState("");
  const [language, setLanguage] = useState("py");
  const myDict = {
    "py": "python",
    "cpp": "cpp",
    "js": "javascript",
    "java": "java",
    "cs": "csharp",
    "go": "go",
  };
 
  // event handlers for the input
  const inputChangeHandler = (event) => {
    event.preventDefault();
    if (event.target.value === null || event.target.value === undefined) {
      setEnteredInput("");
    }
    else {
      setEnteredInput(event.target.value);
    }
  };

  const languageChangeHandler = (event) => {
    event.preventDefault();
    setLanguage(event.target.options[event.target.selectedIndex].value);
  };

  function handleEditorChange(value, event) {
    setEnteredCode(value);
  }

  function handleEditorDidMount(editor, monaco) {
    monaco.editor.defineTheme('my-theme', {
      base: 'vs',
      inherit: true,
      rules: [],
      colors: {
        'editor.background': '#000000',
      },
    });
  }
 
 
  // form submission
   const submitHandler = (event) => {
    event.preventDefault();
    // entered details user object
    const code = {
      input: enteredInput,
      inputCode: enteredCode,
      language : language,
    };
    console.log(code);
    if (enteredCode != null) {
      fetch("http://localhost:8080/code/" + user.id, {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(code),
      }).then(function(response) {
      return response.json();
      })
      .then(function(response) {
        console.log(response);
        setOutput(response);
      })
    .catch(error => console.error(error));
  }
  };
  function handle() {
    const user = {
      name: username
    }
    fetch("http://localhost:8080/User/", {
      method: "POST",
      headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify(user),
    }).then(function(response) {
      console.log(response);
      return response.json();
    }).then(function(response) {
      console.log(response);
      setUser(response);
    }).catch(error => console.error(error));
  }
  if (user == "") {
    return (
      <div>
        <div>
            <input onChange={(e) => {setUsername(e.target.value)}} />
            <button onClick={handle}>Enter Username</button>
        </div>
      </div>
    )
  }
  return (
    <form onSubmit={submitHandler} class="horizontal container center-flex">
      <div class="vertical">
        <div class="horizontal button-gap">
            <select id="ddlViewBy" onChange={languageChangeHandler}>
              <option value="py" selected="selected">Python</option>
              <option value="Lox">Lox</option>
              <option value="BF">BF</option>
              <option value="java">Java</option>
              <option value="cs">C#</option>
              <option value="c">C</option>
              <option value="cpp">C++</option>
              <option value="js">JavaScript</option>
              <option value="go">GoLang</option>
            </select>
            <button type="Submit">Run</button>
          </div>
        <div class="horizontal space-between">
          <div class="vertical overflow-hidden flex-2">
            Code
            <Editor
            height="70vh"
            language={language in myDict ? myDict[language] : ""}
            defaultValue={enteredCode}
            onChange={handleEditorChange}
            onMount={handleEditorDidMount}
            theme="my-theme"
            options={{
              fontSize: 12,
            }}
            />
          </div>
          <div class="vertical space-between flex-1">
            <div class="vertical">
              <label htmlFor="input">Input</label>
              <textarea
                id="input"
                type="textarea"
                class="flex-1"
                value={enteredInput}
                onChange={inputChangeHandler}
              ></textarea>
            </div>
          <Display output={output} />
        </div>
        </div>
      </div>
    </form>
  );
};
 

export default App;
