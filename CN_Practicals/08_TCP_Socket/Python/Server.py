import socket
host = "127.0.0.1"
port = 1234

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((host, port))
server_socket.listen(1)

print("Server Started!")
print("Waiting for a client.")
client_socket, address = server_socket.accept()
print("Client Accepted!")

while True:
    data = client_socket.recv(1024).decode()
    print(data)
    if data == "!#End":
        break
client_socket.close()
server_socket.close()