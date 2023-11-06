import socket
host = "127.0.0.1"
port = 1234

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((host, port))

print("Connected!")

while True:
    message = input("Enter a message: ")
    client_socket.send(message.encode())
    if message == "!#End":
        break
client_socket.close()