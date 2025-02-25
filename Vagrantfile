Vagrant.configure("2") do |config|
  config.vm.box = "bento/ubuntu-22.04"

  config.vm.network "private_network", ip: "192.168.33.10"
  config.vm.boot_timeout = 1000


  config.vm.provider "virtualbox" do |vb|
    vb.memory = "8000"  # Allocate 2GB RAM
    vb.cpus = 4        # Allocate 2 CPUs
  end
end
