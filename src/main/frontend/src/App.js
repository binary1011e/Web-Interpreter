import './App.css';
import React, { useState } from "react";
import Editor from '@monaco-editor/react';
import Display from './Display';
const App = () => {
  // defaults
  const [enteredInput, setEnteredInput] = useState("");
  const [enteredCode, setEnteredCode] = useState("");
  const [output, setOutput] = useState(null);
  const [language, setLanguage] = useState("BF");
 
  // event handlers for input
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
    setLanguage(event.target.options[event.target.selectedIndex].text);
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
      language : language
    };
    console.log(code);
    if (enteredCode != null) {
      fetch("http://localhost:8080/code/", {
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
 
  return (
      <form onSubmit={submitHandler} class="horizontal container center-flex">
        <div class='horizontal'>
          <div class="vertical button-gap">
            <select id="ddlViewBy" onChange={languageChangeHandler}>
              <option value="1">Lox</option>
              <option value="2" selected="selected">BF</option>
            </select>
            <button type="Submit">Run</button>
            <div class='vertical overflow-hidden'>
              <p>Code</p>
              <Editor
              height="60vh"
              defaultLanguage="javascript"
              defaultValue={enteredCode}
              onChange={handleEditorChange}
              onMount={handleEditorDidMount}
              theme="my-theme"
              />
            </div>
          </div>
          <div class='vertical space-between'>
            <label htmlFor="input">Input</label>
            <textarea
              id="input"
              type="textarea"
              value={enteredInput}
              onChange={inputChangeHandler}
            ></textarea>
            <Display output={output} />
          </div>
        </div>
      </form>
  );
};
 

export default App;
