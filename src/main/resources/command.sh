psql -U nooo_sm -d java -f schema.sql > output.log 2>&1


echo "=== START TESTS $(date) ===" > output.log


echo -e "\n=== 1. REGISTRATION ===" >> output.log
echo "Registering user1..." >> output.log
curl -v -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "login": "user1",
    "password": "pass123",
    "balance": 1000.0
  }' \
  >> output.log 2>&1

echo -e "\n=== 2. LOGIN ===" >> output.log
echo "Logging in user1..." >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/auth/signin \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"login": "user1", "password": "pass123"}' \
  >> output.log 2>&1

TOKEN=$(curl -v -s http://localhost:8080/api/auth/signin \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"login": "user1", "password": "pass123"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

echo "Token: $TOKEN" >> output.log


AUTH_HEADER="Authorization: Bearer $TOKEN"

echo -e "\n=== 4. ADD PHONE ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X POST \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"phone": "+79161234567"}' \
  >> output.log 2>&1

echo -e "\n=== 5. ADD EMAIL ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X POST \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"email": "user1@gmail.com"}' \
  >> output.log 2>&1


echo -e "\n=== 6. ADD SECOND PHONE ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X POST \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"phone": "+79161234568"}' \
  >> output.log 2>&1

echo -e "\n=== 6.1 CHECK ALL CONTACTS ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X GET \
  -H "$AUTH_HEADER" \
  >> output.log 2>&1

echo -e "\n=== 7. DELETE FIRST PHONE ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X DELETE \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"phone": "+79161234567"}' \
  >> output.log 2>&1

echo -e "\n=== 7.1 CHECK AFTER DELETE ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X GET \
  -H "$AUTH_HEADER" \
  >> output.log 2>&1


echo -e "\n=== 8. REGISTER SECOND USER ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/auth/signup \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"login": "user2", "password": "pass456", "balance": 500}' \
  >> output.log 2>&1

echo "Failed logging in user2..." >> output.log
curl -v -s http://localhost:8080/api/auth/signin \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"login": "user2", "password": "pass45l6"}'  >> output.log 2>&1

echo "Logging in user2..." >> output.log
TOKEN2=$(curl -v -s http://localhost:8080/api/auth/signin \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"login": "user2", "password": "pass456"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

AUTH_HEADER2="Authorization: Bearer $TOKEN2"


echo -e "\n=== 9. MONEY TRANSFER ===" >> output.log
echo "Transfer 300 from user1 to user2..." >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/payments \
  -X POST \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"amount": 300, "receiver": "user2"}' \
  >> output.log 2>&1


echo -e "\n=== 11. TRANSFER MORE THAN BALANCE ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/payments \
  -X POST \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"amount": 1000, "receiver": "user2"}' \
  >> output.log 2>&1


echo -e "\n=== 12. DELETE EMAIL ===" >> output.log
curl -v -s -w "\nHTTP Status: %{http_code}\n" http://localhost:8080/api/users/profile \
  -X DELETE \
  -H "Content-Type: application/json" \
  -H "$AUTH_HEADER" \
  -d '{"email": "user1@gmail.com"}' \
  >> output.log 2>&1

echo -e "\n=== TESTS COMPLETED $(date) ===" >> output.log