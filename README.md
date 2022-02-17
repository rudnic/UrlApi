# UrlApi

### How to use


##### Get /gen-new-user
Генерирует нового пользователя
В качестве ответа приходит:
{id: _user-id_, secret_key: _user-secret-key_, signature: _user-signature_}
Signature - SHA1(user-id + user-secret-key)
____Example____: curl 'short-url-api-rudnic.herokuapp.com/gen-new-user' -X GET


##### Get /do-shortly?id=_user-id_&url=_url_ 
Получить короткую ссылку
Для того, чтобы запрос корректно отработал, нужно передать свой id и добавить хэдер Signature:_user-signature_
В качестве ответа приходит:
{_source_url_: _token_}
____Example____: curl 'short-url-api-rudnic.herokuapp.com/do-shortly?id=_{your_id}_&url=yandex.ru' -H 'Signature':'_your-signature' -X GET


##### Get /{token}
Перенаправляет на исходный урл по этому токену
Аутентификация не требуется
____Example____: curl 'short-url-api-rudnic.herokuapp.com/K5' -X GET


##### Get /my-urls?id=_user-id_
Получить информацию о созданных урлов
Нужно также указать Signature хэдер
В ответе приходить:
{_source_url_: [_token_, _count_clicks_]
____Example____: curl 'short-url-api-rudnic.herokuapp.com/my-urls?id=_{your_id}_' -H 'Signature':'_your-signature' -X GET

##### Delete /{token}?id=_user-id_
Удалить созданный пользователем токен
Нужно также указать Signature хэдер
____Example____: curl 'short-url-api-rudnic.herokuapp.com/_{token}_?id=_{your_id}_' \  
-H 'Signature':'_your-signature'-X GET