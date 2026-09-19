dùng mã hóa bất đối xứng thuật toán RSA
quản lý bằng keytool 
    
câu lênh tao file cho keystore:
keytool -genkeypair -alias auth-key -keyalg RSA -keysize 2048 -validity 3650 -keystore app-keystore.p12 -storetype PKCS12

certificate: identity-service
