import './App.css';

const Save = ({code, userId}) => {
    async function handle() {
        if (code != null && code != undefined) {
            console.log(code);
            console.log(userId);
            fetch("http://localhost:8080/save/" + userId, {
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
            })
          .catch(error => console.error(error));
        }
    }
    return (
    <div>
        <button type="button" onClick={handle}>Save</button>
    </div>
    ) 
}

export default Save;
