import { useEffect, useRef, useState } from "react";
import axios from "axios";
import "./App.css";
import { FaRegCopy } from "react-icons/fa6";
import copy from "copy-to-clipboard";

function App() {
  const [urlObj, SetUrlObj] = useState({
    longUrl: "",
    shortUrl: "",
  });

  const [isCopy, SetIsCopy] = useState(false);

  const handleInputChange = (event) => {
    const { name, value } = event.target;

    SetUrlObj((values) => ({
      ...values,
      [name]: value,
      shortUrl:""
    }));
    SetIsCopy(false);
  };

  const handleSubmit = async (event) => {
    console.log(urlObj);
    const response = await axios
      .post("http://localhost:8080/shorten", urlObj)
      .then((res) => {
        console.log(res);
        if (res.data.url != null) {
          SetUrlObj((values) => ({
            ...values,
            shortUrl: res.data.url,
          }));
        } else {
          SetUrlObj((values) => ({
            ...values,
            shortUrl: "Invalid URL",
          }));
        }
      });
  };

  const textRef = useRef();
  const copyToClipboard = () => {
    let copyText = textRef.current.value;
    let copyCheck = copy(copyText);
    if (copyCheck) {
      SetIsCopy(true);
    }
  };

  return (
    <>
      <div>
        <h1>Shorten your</h1>
        <h1>----&gt;| URL |&lt;-----</h1>
      </div>
      <form id="former">
        <div className="inputBox">
          <input
            className="inpTxt"
            type="text"
            name="longUrl"
            id="longUrl"
            onChange={handleInputChange}
            placeholder="Enter any URL to shorten"
            value={urlObj.longUrl}
          />
        </div>
        <div className="btnBox">
          <input
            type="button"
            value="Submit"
            onClick={handleSubmit}
            className="submitBtn"
          />
        </div>
        <div className="captionText">Submit to get the shortened URL below</div>
        <div className="">
          <input
            className="outputTxt"
            type="text"
            name="shortUrl"
            id="shortUrl"
            value={urlObj.shortUrl}
            ref={textRef}
            onChange={handleInputChange}
            disabled
          />
          <button type="button" className="copyBtn" onClick={copyToClipboard}>
            <FaRegCopy />
          </button>
        </div>
        {isCopy && <div className="captionText">Copied to clipboard !</div>}
      </form>
    </>
  );
}

export default App;
