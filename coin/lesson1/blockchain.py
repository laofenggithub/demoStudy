# {
#     "index":0,
#     "timestamp":"",
#     "transactions":[
#         {
#             "sender":"",
#             "recipient":"",
#             "amount":5
#         }
#     ],
#     "proof":"",
#     "previous_hash":"",
# }
import hashlib
import json
from time import time, sleep

from flask import Flask


class Blockchain:
    def __init__(self):
        self.chain = [];
        self.current_transactions = []
        self.new_block(proof=100, previous_hash=1)

    def new_block(self, proof, previous_hash=None):
        block = {
            'index': len(self.chain) + 1,
            'timestamp': time(),
            'transcations': self.current_transactions,
            'proof': proof,
            'previous_hash': previous_hash or self.hash(self.last_block)
        }
        self.current_transactions = []
        self.chain.append(block)

    def new_transaction(self, sender, recipient, amount) -> int:
        self.current_transactions.append(
            {
                'sender': sender,
                'recipient': recipient,
                'amount': amount
            }
        )
        return self.last_block['index'] + 1

    @staticmethod
    def hash(self, block):
        block_string = json.dumps(block, sort_keys=True).encode()
        return hashlib.sha256(block_string).hexdigest()

    @property
    def last_block(self):
        self.chain[-1]

    def proof_of_work(self, last_proof: int) -> int:
        proof = 0
        while self.valid_proof(last_proof, proof) is False:
            proof += 1
        print(proof)
        return proof

    def valid_proof(self, last_proof: int, proof: int) -> bool:
        guess = f'{last_proof}{proof}'.encode()
        guess_hash = hashlib.sha256(guess).hexdigest()
        # sleep(0.01)
        print(proof, guess_hash)
        return guess_hash[0:4] == "0000"


app = Flask(__name__)


@app.route('/index', methods=['GET'])
def index():
    return "Hello BlockChain"


@app.route('/transactions/new', methods=['POST'])
def newTransactions():
    return "we'll add a new transctions"

@app.route('/mine',methods=['GET'])
def mine():
    return "we'll mine a new block"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)

# if __name__ == "__main__":
#     testPow = Blockchain()
#     testPow.proof_of_work(100)
