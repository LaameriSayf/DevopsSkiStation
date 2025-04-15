Vagrant.configure("2") do |config|
  config.vm.box = "bento/ubuntu-22.04"

  config.vm.network "private_network", ip: "192.168.33.10"
  config.vm.boot_timeout = 600

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "8000"
    vb.cpus = 4
  end

  # 🔐 Use the default insecure key so you don't get prompted for a password
  config.ssh.insert_key = false
end
