import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

const API = 'http://localhost:8080/api';

function App() {
  const [username, setUsername] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [score, setScore] = useState(0);
  const [draws, setDraws] = useState(0);
  const [lastResult, setLastResult] = useState(null);
  const [stats, setStats] = useState(null);
  const [leaderboard, setLeaderboard] = useState(null);
  const [error, setError] = useState('');

  const login = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(`${API}/user`, { username });
      setScore(res.data.score);
      setDraws(res.data.drawCount);
      setLoggedIn(true);
      setError('');
    } catch (err) {
      setError(err.response?.data || err.message);
    }
  };

  const play = async (move) => {
    try {
      const res = await axios.post(`${API}/game/play`, { username, move });
      setLastResult(res.data);
      setScore(res.data.score);
      setDraws(res.data.drawCount);
      setError('');
    } catch (err) {
      setError(err.response?.data || err.message);
    }
  };

  const loadStats = async () => {
    try {
      const res = await axios.get(`${API}/stats/${username}`);
      setStats(res.data);
      setError('');
    } catch (err) {
      setError(err.response?.data || err.message);
    }
  };

  const loadLeaderboard = async () => {
    try {
      const res = await axios.get(`${API}/leaderboard`);
      setLeaderboard(res.data);
      setError('');
    } catch (err) {
      setError(err.response?.data || err.message);
    }
  };

  if (!loggedIn) {
    return (
        <div className="container">
          <h1>Rock - Paper - Scissors</h1>
          <form onSubmit={login}>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <button type="submit">Intră în joc</button>
          </form>
          {error && <p className="error">{error}</p>}
        </div>
    );
  }

  return (
      <div className="container">
        <h1>Rock - Paper - Scissors</h1>
        <p>Utilizator: <b>{username}</b></p>

        {error && <p className="error">{error}</p>}

        <div className="score-board">
          <p>Scor curent: <b>{score}</b></p>
          <p>Egaluri: <b>{draws}</b></p>
        </div>

        <div className="buttons">
          <button onClick={() => play('ROCK')}>Rock</button>
          <button onClick={() => play('PAPER')}>Paper</button>
          <button onClick={() => play('SCISSORS')}>Scissors</button>
        </div>

        {lastResult && (
            <div className="result">
              <p>User: {lastResult.playerMove} | Server: {lastResult.serverMove}</p>
              <p>Rezultat: <b>{lastResult.result}</b></p>
              {lastResult.gameOver && <p className="game-over">Ai pierdut! S-a pornit un joc nou.</p>}
            </div>
        )}

        <div className="actions">
          <button onClick={loadStats}>Statistici</button>
          <button onClick={loadLeaderboard}>Clasament</button>
        </div>

        {stats && (
            <div className="stats">
              <p>Jocuri jucate: {stats.totalGamesPlayed}</p>
              <p>Scor maxim: {stats.highestScore}</p>
            </div>
        )}

        {leaderboard && (
            <table>
              <thead>
              <tr>
                <th>Username</th>
                <th>Scor</th>
                <th>Egaluri</th>
                <th>Timestamp</th>
              </tr>
              </thead>
              <tbody>
              {leaderboard.map((entry, idx) => (
                  <tr key={idx}>
                    <td>{entry.username}</td>
                    <td>{entry.finalScore}</td>
                    <td>{entry.drawCount}</td>
                    <td>{entry.timestamp}</td>
                  </tr>
              ))}
              </tbody>
            </table>
        )}
      </div>
  );
}

export default App;