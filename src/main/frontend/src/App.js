import React, {useState, useEffect, useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import {useDropzone} from 'react-dropzone'


const UserProfiles = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((profile, index) => {
    return (
      <div key={index}>
        <img 
          src={`http://localhost:8080/api/v1/user-profile/${profile.userProfileId}/image/download`} 
        />
        <br/>
        <br/>
        <h1> Name : {profile.userName}</h1>
        <p> Id : {profile.userProfileId}</p>
        <Dropzone {...profile} />
        <br/>
      </div>
    )
  })
};

function Dropzone({ userProfileId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];

    console.log(file);

    const form = new FormData();
    form.append("file", file);

    axios.post(
      `http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      form,
      {
        headers: {
          "Content-Type": "multipqrt/form-data"
        }
      }
    )
    .then(() => {
      console.log("File uploaded sucessfully");
    })
    .catch(err => {
      console.log(err)
    });
  }, []);
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
        {
        isDragActive ?
          <p>Drop the profile image here ...</p> :
          <p>Drag 'n' drop profile image  files here, or click to select profile image </p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
