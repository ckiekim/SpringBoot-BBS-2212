from flask import Flask, render_template, request
from urllib.parse import unquote
import pandas as pd
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
import json

app = Flask(__name__)

@app.before_first_request
def before_first_request():
    global model, df
    model = SentenceTransformer('jhgan/ko-sroberta-multitask')
    df = pd.read_csv('wellness_dataset.csv')
    df['embedding'] = df.embedding.apply(json.loads)
    print('Initialization is done.')

@app.route('/')
def index():
    user_input = '걱정이 돼서 밤에 잠이 잘 안 와.'
    embedding = model.encode(user_input)
    df['유사도'] = df.embedding.map(lambda x: cosine_similarity([embedding], [x]).squeeze())
    answer = df.loc[df.유사도.idxmax()]
    return f'<h1>{user_input}</h1><h1>{answer.챗봇}</h1>'

@app.route('/chatbot')
def chatbot():
    user_input = unquote(request.args.get('userInput'))
    embedding = model.encode(user_input)
    df['유사도'] = df.embedding.map(lambda x: cosine_similarity([embedding], [x]).squeeze())
    answer = df.loc[df.유사도.idxmax()]
    result = {
        'category': answer.구분, 'user': user_input, 'chatbot': answer.챗봇, 'similarity': answer.유사도
    }
    return json.dumps(result)

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)