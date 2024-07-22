psql -U nooo_sm -d java -f schema.sql > output.log 2>&1

echo 'регистрация' >> output.log 2>&1
curl http://localhost:8080/signin -X POST  -H "Content-Type: application/json" -d '{login = “”, password = “”, tlf = "89", email = “”, balance = “1000”, birthday = date, fio = “”}' >> output.log 2>&1
echo 'проверка регистрации' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1
#
#
#
echo 'смена номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X PUT -H "Content-Type: application/json" -d '{tlf="2222222"}' >> output.log 2>&1
echo 'проверка номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'смена email' >> output.log 2>&1
curl http://localhost:8080/users/me -X PUT -H "Content-Type: application/json" -d '{email="2@gmail.ru"}' >> output.log 2>&1
echo 'проверка email' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'смена всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X PUT --H "Content-Type: application/json" d '{tlf="3333333" email="3@gmail.ru"}' >> output.log 2>&1
echo 'проверка всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1
#
#
echo 'добавление номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X POST -H "Content-Type: application/json" -d '{tlf="4444444"}' >> output.log 2>&1
echo 'проверка номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'добавление email' >> output.log 2>&1
curl http://localhost:8080/users/me -X POST -H "Content-Type: application/json" -d '{email="4@gmail.ru"}' >> output.log 2>&1
echo 'проверка email' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'добавление всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X POST -H "Content-Type: application/json" -d '{tlf="5" email="5@gmail.ru"}' >> output.log 2>&1
echo 'проверка всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1
#
#
echo 'удаление номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X DELETE -H "Content-Type: application/json" -d '{tlf="4444444"}' >> output.log 2>&1
echo 'проверка номера телефона' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'удаление email' >> output.log 2>&1
curl http://localhost:8080/users/me -X DELETE -H "Content-Type: application/json" -d '{email="4@gmail.ru"}' >> output.log 2>&1
echo 'проверка email' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'удаление всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X DELETE -H "Content-Type: application/json" -d '{tlf="5" email="5@gmail.ru"}' >> output.log 2>&1
echo 'проверка всего' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1
#
#
#
echo 'отправка денег' >> output.log 2>&1
curl  http://localhost:8080/payments -X POST-H "Content-Type: application/json" -d '{"amount": 900,"receiver": "Ann"}' >> output.log 2>&1
echo 'проверка денег' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'отправка денег без баланса' >> output.log 2>&1
curl  http://localhost:8080/payments -X POST-H "Content-Type: application/json" -d '{"amount": 900,"receiver": "Ann"}' >> output.log 2>&1
echo 'проверка денег без баланса' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1

echo 'вход в систему' >> output.log 2>&1
curl http://localhost:8080/signup -X POST -H "Content-Type: application/json" -d 'login = “Ann”, password = “?”' >> output.log 2>&1
echo 'проверка баланса у 2 (1000)' >> output.log 2>&1
curl http://localhost:8080/users/me -X GET >> output.log 2>&1