require('dotenv').config();
const express = require('express');
const axios = require('axios');
const app = express();

app.use(express.json());

app.post('/chat', async (req, res) => {
  const userMessage = req.body.message;

  try {
    const response = await axios.post(
      'https://api.openai.com/v1/chat/completions',  // or OpenRouter endpoint
      {
        model: 'gpt-3.5-turbo', // or use OpenRouter model
        messages: [
          { role: 'system', content: 'You are a helpful assistant.' },
          { role: 'user', content: userMessage }
        ]
      },
      {
        headers: {
          'Authorization': `Bearer ${process.env.OPENAI_API_KEY}`,
          'Content-Type': 'application/json'
        }
      }
    );

    const aiReply = response.data.choices[0].message.content;
    res.json({ reply: aiReply });
  } catch (error) {
    console.error('AI API error:', error.response?.data || error.message);
    res.status(500).json({ reply: 'Something went wrong.' });
  }
});

app.get('/', (req, res) => {
  res.send('Server is working!');
});
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 Server running at http://localhost:${PORT}`);
});