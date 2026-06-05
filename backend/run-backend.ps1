$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptDir

Write-Host "Compiling backend sources..."
$javaFiles = Get-ChildItem -Path .\src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -cp .\lib\mysql-connector-j-8.4.0.jar -d .\out $javaFiles

if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed. Fix the Java errors above."
    exit $LASTEXITCODE
}

Write-Host "Running backend main class..."
java -cp ".\out;.\lib\mysql-connector-j-8.4.0.jar" com.studentportfolio.Main
