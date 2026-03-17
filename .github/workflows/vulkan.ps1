# Download the latest Vulkan SDK ZIP
$zipUrl = "https://sdk.lunarg.com/sdk/download/latest/windows/vulkan-sdk.zip"
$zipPath = "$env:TEMP\VulkanSDK.zip"
Invoke-WebRequest -Uri $zipUrl -OutFile $zipPath

# Extract using 7-Zip
$extractDir = "$env:USERPROFILE\VulkanSDK"
& "C:\Program Files\7-Zip\7z.exe" x $zipPath "-o$extractDir" -y

# Find extracted version folder
$versionDir = Get-ChildItem $extractDir | Sort-Object Name -Descending | Select-Object -First 1
$fullPath = Join-Path $extractDir $versionDir.Name

# Set environment variables for current session
$env:VULKAN_SDK = $fullPath
$env:PATH = "$fullPath\Bin;$env:PATH"

# Persist for future PowerShell sessions
[Environment]::SetEnvironmentVariable("VULKAN_SDK", $fullPath, "User")
$oldPath = [Environment]::GetEnvironmentVariable("PATH", "User")
[Environment]::SetEnvironmentVariable("PATH", "$fullPath\Bin;$oldPath", "User")

# Verify installation
Write-Host "VULKAN_SDK = $env:VULKAN_SDK"
& "$env:VULKAN_SDK\vulkaninfo.exe" | Select-String "VK_LAYER_KHRONOS_validation"
